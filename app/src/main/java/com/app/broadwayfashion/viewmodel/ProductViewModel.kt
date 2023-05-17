package com.app.broadwayfashion.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.app.broadwayfashion.base.BaseViewModel
import com.app.broadwayfashion.model.product.CategoryListResponse
import com.app.broadwayfashion.model.product.ProductItem
import com.app.broadwayfashion.model.product.ProductListResponse
import com.app.broadwayfashion.network.ApiService
import com.app.broadwayfashion.network.ParentApiService
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.utils.AppSharePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSharePreferences: AppSharePreferences,
    private val apiService: ParentApiService,
) : BaseViewModel(context) {

    private val _productListLoad: MutableSharedFlow<ProductListResponse?> = MutableSharedFlow()
    val productListLoad: SharedFlow<ProductListResponse?> = _productListLoad

    private val _categoryListLoad: MutableSharedFlow<CategoryListResponse?> = MutableSharedFlow()
    val categoryListLoad: SharedFlow<CategoryListResponse?> = _categoryListLoad

    private val _productLoad: MutableSharedFlow<ProductItem?> = MutableSharedFlow()
    val productLoad: SharedFlow<ProductItem?> = _productLoad


    fun getProductList(index: Int) {
        viewModelScope.launch {
            var res = apiService.getAllProduct(
                index.toString(),
                AppConfigurations.PRODUCT_ITEM_COUNT.toString()
            )
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _productListLoad.emit(res.body())
            } else {
                _productListLoad.emit(null)
            }
        }

    }

    fun getCategoryList(id: String) {
        viewModelScope.launch {
            var res = apiService.getSubCategory(id)
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _categoryListLoad.emit(res.body())
            } else {
                _productListLoad.emit(null)
            }
        }

    }

    fun getProductListByCat(cat: Int, index: Int) {
        viewModelScope.launch {
            var res = apiService.getProductListByCategoryMap2(
                "pa_size",
                AppConfigurations.size,
                "pa_colour",
                AppConfigurations.color,
                "pa_brand",
                AppConfigurations.brand,
                AppConfigurations.orderBy,
                AppConfigurations.order,
                cat.toString(),
                index.toString(),
                AppConfigurations.PRODUCT_ITEM_COUNT.toString()
            )
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _productListLoad.emit(res.body())
            } else {
                _productListLoad.emit(null)

            }
        }

    }

    fun getProductListByName(text: String, index: Int) {
        viewModelScope.launch {
            var res = apiService.getProductListByText(
                text,
                index.toString(),
                AppConfigurations.PRODUCT_ITEM_COUNT.toString()
            )
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _productListLoad.emit(res.body())
            } else {
                _productListLoad.emit(null)
            }
        }

    }

    fun getProductDetail(id: String) {
        viewModelScope.launch {
            var res = apiService.getProductDetailById(id)
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _productLoad.emit(res.body())
            } else {
                _productLoad.emit(null)
            }
        }
    }
    fun getProductDetailVariations(id: String) {
        viewModelScope.launch {
            var res = apiService.getProductDetailById(id)
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _productLoad.emit(res.body())
            } else {
                _productLoad.emit(null)
            }
        }
    }
    fun getSimilarProduct(string: String) {
        viewModelScope.launch {
            var res = apiService.getSimilarProduct(
                string
            )
            if (isResponseSuccess(res = res as Response<Any?>)) {
                _productListLoad.emit(res.body())
            } else {
                _productListLoad.emit(null)
            }
        }
    }
}