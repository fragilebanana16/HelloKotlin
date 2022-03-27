package com.example.jaydean.tutorial.data

/**
 * Created by jaydean on 2022/3/27.
 */
import com.example.jaydean.tutorial.model.Affirmation
import com.example.jaydean.tutorial.R

class Datasource {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
                Affirmation(R.string.affirmation1),
                Affirmation(R.string.affirmation2),
                Affirmation(R.string.affirmation3),
                Affirmation(R.string.affirmation4),
                Affirmation(R.string.affirmation5),
                Affirmation(R.string.affirmation6),
                Affirmation(R.string.affirmation7),
                Affirmation(R.string.affirmation8),
                Affirmation(R.string.affirmation9),
                Affirmation(R.string.affirmation10),
                Affirmation(R.string.affirmation11),
                Affirmation(R.string.affirmation12),
                Affirmation(R.string.affirmation13),
                Affirmation(R.string.affirmation14),
                Affirmation(R.string.affirmation15),
                Affirmation(R.string.affirmation16),
                Affirmation(R.string.affirmation17),
                Affirmation(R.string.affirmation18)
        )
    }
}