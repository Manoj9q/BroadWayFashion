package com.app.broadwayfashion.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.app.broadwayfashion.base.BaseViewModel
import com.app.broadwayfashion.model.BillingUpdateRequest
import com.app.broadwayfashion.model.ShippingUpdateRequest
import com.app.broadwayfashion.model.customer.Billing
import com.app.broadwayfashion.model.customer.Shipping
import com.app.broadwayfashion.network.JwtApiService
import com.app.broadwayfashion.network.ParentApiService
import com.app.broadwayfashion.utils.AppSharePreferences
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.commonApiErrorMessage
import com.app.broadwayfashion.utils.errorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSharePreferences: AppSharePreferences,
    private val jwtApiService: JwtApiService,
    private val apiService: ParentApiService
) : BaseViewModel(context) {

    fun updateProfileAddress(request: Shipping, billing: Billing) = viewModelScope.launch {
       /* apiService.updateUserBilling(
            appSharePreferences.getString(USER_SELF_ID),
            BillingUpdateRequest(billing)
        )*/
        apiService.updateUserAddress(
            appSharePreferences.getString(USER_SELF_ID),
            ShippingUpdateRequest(request,billing)
        ).let { res ->
            if (isResponseSuccess(res = res as Response<Any?>)) {
                setSuccessResponse("Shipping Address updated successfully.")
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
            }
        }
    }

}