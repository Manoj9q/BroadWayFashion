package com.app.broadwayfashion.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.broadwayfashion.base.BaseViewModel
import com.app.broadwayfashion.model.UserSignupResponse
import com.app.broadwayfashion.model.admintoken.AdminTokenResponse
import com.app.broadwayfashion.model.customer.CustomerDataResponse
import com.app.broadwayfashion.model.forgotpassword.ForgotPasswordResponse
import com.app.broadwayfashion.model.request.SignupRequest
import com.app.broadwayfashion.model.response.LoginResponse
import com.app.broadwayfashion.network.ApiService
import com.app.broadwayfashion.network.JwtApiService
import com.app.broadwayfashion.network.ParentApiService
import com.app.broadwayfashion.utils.AppSharePreferences
import com.app.broadwayfashion.utils.LAST_ADMIN_TOKEN_SYNC
import com.app.broadwayfashion.utils.LAST_LOGIN_USER_ID_EMAIL
import com.app.broadwayfashion.utils.LAST_PASSWORD
import com.app.broadwayfashion.utils.PARENT_AUTH_TOKEN
import com.app.broadwayfashion.utils.PASSWORD
import com.app.broadwayfashion.utils.USER_AUTH_TOKEN
import com.app.broadwayfashion.utils.USER_EMAIL
import com.app.broadwayfashion.utils.USER_FIRST_NAME
import com.app.broadwayfashion.utils.USER_LAST_NAME
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.USER_USER_NAME_ID
import com.app.broadwayfashion.utils.commonApiErrorMessage
import com.app.broadwayfashion.utils.errorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSharePreferences: AppSharePreferences,
    private val apiService: ApiService,
    private val parentApiService: ParentApiService,
    private val jwtApiService: JwtApiService
) : BaseViewModel(context) {

    private val _adminTokenResponse = MutableLiveData<AdminTokenResponse?>()
    val adminTokenResponse: LiveData<AdminTokenResponse?> = _adminTokenResponse


    private val _loginData: MutableSharedFlow<LoginResponse?> = MutableSharedFlow()
    val loginData: SharedFlow<LoginResponse?> = _loginData

    private val _apiErrorOnLoad: MutableSharedFlow<String> = MutableSharedFlow()
    val apiErrorOnLoad: SharedFlow<String> = _apiErrorOnLoad

    private val _userLoadData: MutableSharedFlow<CustomerDataResponse?> = MutableSharedFlow()
    val userLoadData: SharedFlow<CustomerDataResponse?> = _userLoadData

    private val _userSignupData: MutableSharedFlow<UserSignupResponse?> = MutableSharedFlow()
    val userSignupData: SharedFlow<UserSignupResponse?> = _userSignupData

    private val _emailOtpResponse: MutableSharedFlow<ForgotPasswordResponse?> = MutableSharedFlow()
    val emailOtpResponse: SharedFlow<ForgotPasswordResponse?> = _emailOtpResponse

    private val _signupErrorLiveData = MutableLiveData<String?>()
    val signupErrorLiveData: LiveData<String?> = _signupErrorLiveData

    fun userLogin(userName: String, password: String) = viewModelScope.launch {
        setLoading(false)
        var map = HashMap<String, String>()
        map["username"] = userName
        map["password"] = password
        parentApiService.userLogin(map).let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                setUserToken(res.body()?.token ?: "")
                _loginData.emit(res.body())
                getUserId(map, userName, password)
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse("Invalid Credentials" ?: commonApiErrorMessage)
            }
        }
    }

    private fun getUserId(map: HashMap<String, String>, userName: String, password: String) =
        viewModelScope.launch {
            setLoading(true)
            apiService.getUserId(map).let { res ->
                if (isResponseSuccess(res = res as Response<Any?>)) {
                    var userId = (res.body()?.id ?: 0).toString()
                    setUserID(userId)
                    getUserDetail(userId, userName, password)
                } else {
                    var errorResponse = errorResponse(res as Response<Any>)
                    setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
                }
            }
        }

    private fun getUserDetail(id: String, userName: String?, password: String?) =
        viewModelScope.launch {
            parentApiService.getUserDetail(id).let { res ->
                if (isResponseSuccess(res = res as Response<Any?>)) {
                    setUserInfo(res.body()!!, userName,password)
                    _userLoadData.emit(res.body())
                } else {
                    var errorResponse = errorResponse(res as Response<Any>)
                    setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
                }
            }
        }

    fun getUserDetailById(id: String) = viewModelScope.launch {
        parentApiService.getUserDetail(id).let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _userLoadData.emit(res.body())
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
            }
        }
    }

    fun signupRequest(request: SignupRequest) = viewModelScope.launch {
        parentApiService.userSignUp(request).let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                setSuccessResponse("User registered successfully")
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
            }
        }
    }

    fun setOtpPasswordRequest(email: String) = viewModelScope.launch {
        parentApiService.forgotPasswordOtpRequest(email).let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _emailOtpResponse.emit(res.body())
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
            }
        }
    }

    fun setNewPassword(email: String, password: String, code: String) = viewModelScope.launch {
        parentApiService.setNewPassword(email, password, code).let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _emailOtpResponse.emit(res.body())
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
            }
        }
    }

    fun updateProfile(fName: String, lName: String, email: String) = viewModelScope.launch {
        var customerId = appSharePreferences.get<String>(USER_SELF_ID) ?: ""
        parentApiService.updateProfile(customerId, fName, lName, email).let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                var userId = (res.body()?.id ?: 0).toString()
                setUserID(userId)
                getUserDetail(userId, null, null)
                setSuccessResponse("Profile updated successfully.")
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
            }
        }
    }


    fun getAdminToken() = viewModelScope.launch {
        jwtApiService.getAdminToken("Prakash", "Techmave1").let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                setAdminBearerToken(res.body()?.token ?: "")
            }
        }
    }

    private fun setUserToken(token: String) {
        appSharePreferences.setString(USER_AUTH_TOKEN, token)
    }

    private fun setAdminBearerToken(token: String) {
        appSharePreferences.put(System.currentTimeMillis(), LAST_ADMIN_TOKEN_SYNC)
        appSharePreferences.setString(PARENT_AUTH_TOKEN, token)
    }

    private fun setUserID(id: String) {
        appSharePreferences.setString(USER_SELF_ID, id)
    }

    private fun setUserInfo(info: CustomerDataResponse, userName: String?, password: String?) {
        if (!password.isNullOrEmpty()) {
            appSharePreferences.setString(PASSWORD, password)
            appSharePreferences.setString(LAST_LOGIN_USER_ID_EMAIL, userName ?: "")
            appSharePreferences.setString(LAST_PASSWORD, password ?: "")
        }
        appSharePreferences.setString(USER_SELF_ID, info.id.toString())

        appSharePreferences.setString(USER_FIRST_NAME, info.firstName ?: "")
        appSharePreferences.setString(USER_LAST_NAME, info.lastName ?: "")
        appSharePreferences.setString(USER_EMAIL, info.email ?: "")
        appSharePreferences.setString(USER_USER_NAME_ID, info.username ?: "")

    }


}