package com.quiz.repo.addressRepo

import com.quiz.repo.Model.Address
import com.quiz.util.Resource

interface AddressRepo {

    suspend fun add_address(address: Address) : Resource<Boolean>

    suspend fun delete_add(id : String) : Resource<Boolean>

    suspend fun edit_add(id : String, address: Address) : Resource<Boolean>

}