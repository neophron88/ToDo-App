package org.rasulov.todoapp.presentation.utils


data class UiState<T>(
    val data: List<T> = emptyList(),
    val isLoading: Boolean = false
) {
    val isEmptyData get() = !isLoading && data.isEmpty()
    val isNotEmptyData get() = !isLoading && data.isNotEmpty()
}