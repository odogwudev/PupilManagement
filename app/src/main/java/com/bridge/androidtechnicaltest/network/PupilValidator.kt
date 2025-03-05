package com.bridge.androidtechnicaltest.network

import com.bridge.androidtechnicaltest.data.local.Pupil

object PupilValidator {

    private const val IMAGE_URL_MIN_LENGTH = 11

    /**
     * Returns a list of errors if invalid; empty if valid.
     */
    fun validate(pupil: Pupil): List<String> {
        val errors = mutableListOf<String>()

        // Name must not be blank
        if (pupil.name.isBlank()) {
            errors += "Name must not be empty."
        }

        // Country must not be blank
        if (pupil.country.isBlank()) {
            errors += "Country must not be empty."
        }

        // Image must have at least 11 characters
        if (pupil.image.length < IMAGE_URL_MIN_LENGTH) {
            errors += "Image must be at least $IMAGE_URL_MIN_LENGTH characters long."
        }

        // Check lat/long ranges
        if (pupil.latitude !in -90.0..90.0) {
            errors += "Latitude is out of range: ${pupil.latitude}"
        }
        if (pupil.longitude !in -180.0..180.0) {
            errors += "Longitude is out of range: ${pupil.longitude}"
        }

        return errors
    }
}
