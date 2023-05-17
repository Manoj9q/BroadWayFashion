package com.app.broadwayfashion.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.app.broadwayfashion.base.BaseViewModel
import com.app.broadwayfashion.model.order.OrderResponse
import com.app.broadwayfashion.model.orderrequest.OrderRequestModel
import com.app.broadwayfashion.network.ApiService
import com.app.broadwayfashion.network.JwtApiService
import com.app.broadwayfashion.network.ParentApiService
import com.app.broadwayfashion.utils.AppSharePreferences
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
class OrderViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSharePreferences: AppSharePreferences,
    private val apiService: ApiService,
    private val parentApiService: ParentApiService,
    private val jwtApiService: JwtApiService
) : BaseViewModel(context) {

    private val _orderResponse: MutableSharedFlow<OrderResponse?> = MutableSharedFlow()
    val orderResponse: SharedFlow<OrderResponse?> = _orderResponse

    fun getOderList(customerId: String) = viewModelScope.launch {
        var res = parentApiService.getOrderList(customerId, "1", "50")
        if (isResponseSuccess(res = res as Response<Any?>)) {
            _orderResponse.emit(res.body())
        } else {
            var errorResponse = errorResponse(res as Response<Any>)

            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }

    fun createOrder(request: OrderRequestModel) = viewModelScope.launch {
        var res = parentApiService.createOrder(request)
        if (isResponseSuccess(res = res as Response<Any?>)) {
            setSuccessResponse("success")
        } else {
            var errorResponse = errorResponse(res as Response<Any>)

            setErrorResponse(errorResponse?.message ?: commonApiErrorMessage)
        }
    }

}