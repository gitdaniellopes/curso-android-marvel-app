package br.com.core.data.repository

import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    val sorting: Flow<String>
    suspend fun saveSorting(sorting: String)
}