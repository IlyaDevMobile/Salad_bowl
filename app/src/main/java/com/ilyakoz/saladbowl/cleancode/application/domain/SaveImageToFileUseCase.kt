package com.ilyakoz.saladbowl.cleancode.application.domain

import android.net.Uri

class SaveImageToFileUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun saveImageToFile(imageUri: Uri): String{
        return saladBowlRepository.saveImageToFile(imageUri)
    }

}