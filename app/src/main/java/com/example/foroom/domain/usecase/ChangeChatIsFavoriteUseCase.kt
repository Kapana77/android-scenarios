package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class ChangeChatIsFavoriteUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke(id: Int, isFavorite: Boolean) {
        if (isFavorite) repository.favoriteChat(id)
        else repository.unfavoriteChat(id)
    }

}