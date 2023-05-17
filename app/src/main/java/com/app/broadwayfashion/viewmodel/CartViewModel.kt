package com.app.broadwayfashion.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.app.broadwayfashion.base.BaseViewModel
import com.app.broadwayfashion.model.cart.AddToCartResponse
import com.app.broadwayfashion.network.JwtApiService
import com.app.broadwayfashion.utils.AppSharePreferences
import com.app.broadwayfashion.utils.PASSWORD
import com.app.broadwayfashion.utils.USER_EMAIL
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
class CartViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSharePreferences: AppSharePreferences,

    private val jwtApiService: JwtApiService
) : BaseViewModel(context) {
    private var userName: String? = null
    private var password: String? = null

    private val _cartAdded: MutableSharedFlow<AddToCartResponse?> = MutableSharedFlow()
    val cartAdded: SharedFlow<AddToCartResponse?> = _cartAdded

    init {
         userName = appSharePreferences.getString(USER_EMAIL)
         password = appSharePreferences.getString(PASSWORD)
    }

    fun addToCart(cartKey: String, quantity: String, id: String, productId: String) =
        viewModelScope.launch {
            var res = jwtApiService.addToCart(cartKey, id, quantity, productId)
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _cartAdded.emit(res.body())
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)

            }
        }

    fun addToCartByUser(quantity: String, id: String, productId: String) =
        viewModelScope.launch {

            var res = jwtApiService.addToCartByUser(
                userName ?: "",
                password ?: "",
                id,
                quantity,
                productId
            )
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _cartAdded.emit(res.body())
            } else {
                var errorResponse = errorResponse(res as Response<Any>)
                setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)

            }
        }

    fun getCartInfo(cartKey: String) = viewModelScope.launch {
        var res = jwtApiService.getCartInfo(cartKey)
        if (isResponseSuccess(res = res as Response<Any?>)) {
            _cartAdded.emit(res.body())
        } else {
            var errorResponse = errorResponse(res as Response<Any>)

            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }
    fun getCartInfoByUser() = viewModelScope.launch {
        var res = jwtApiService.getCartInfoOfUser(userName?:"",password?:"")
        if (isResponseSuccess(res = res as Response<Any?>)) {
            _cartAdded.emit(res.body())
        } else {
            var errorResponse = errorResponse(res as Response<Any>)

            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }
    fun removeCartItem(itemKey: String, cartKey: String) = viewModelScope.launch {
        var res = jwtApiService.removeItemCart(itemKey, cartKey)
        if (isResponseSuccess(res = res as Response<Any?>)) {
            _cartAdded.emit(res.body())
        } else {
            var errorResponse = errorResponse(res as Response<Any>)
            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }
    fun removeCartItemByUser(itemKey: String) = viewModelScope.launch {
        var res = jwtApiService.removeItemCartByUser(itemKey,userName?:"",password?:"")
        if (isResponseSuccess(res = res as Response<Any?>)) {
            _cartAdded.emit(res.body())
        } else {
            var errorResponse = errorResponse(res as Response<Any>)
            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }
    fun updateCartItem(itemKey: String, quantity: String, cartKey: String) = viewModelScope.launch {
        var res = jwtApiService.updateCartQuantity(itemKey, quantity, cartKey)
        if (isResponseSuccess(res = res as Response<Any?>)) {
            _cartAdded.emit(res.body())
        } else {
            var errorResponse = errorResponse(res as Response<Any>)
            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }

    fun clearAllCart() = viewModelScope.launch {
        var res =  jwtApiService.clearAllCart(userName?:"",password?:"")
        if (isResponseSuccess(res = res as Response<Any?>)) {
        //   _cartAdded.emit(res.body())
        } else {
          //  var errorResponse = errorResponse(res as Response<Any>)
          //  setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }

    fun updateCartItemByUser(itemKey: String, quantity: String) = viewModelScope.launch {
        var res = jwtApiService.updateCartQuantityByUser(
            itemKey,
            quantity,
            userName ?: "",
            password ?: ""
        )
        if (isResponseSuccess(res = res as Response<Any?>)) {
            _cartAdded.emit(res.body())
        } else {
            var errorResponse = errorResponse(res as Response<Any>)
            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }
}