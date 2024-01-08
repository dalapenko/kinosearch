package tech.dalapenko.core.basepresentation.navigate

import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import tech.dalapenko.core.basepresentation.R

object Animation {

    fun slideRight(
        @IdRes destinationId: Int,
        inclusive: Boolean = true,
        saveState: Boolean = true
    ): NavOptions {
        return NavOptions.Builder()
            .setPopUpTo(
                destinationId = destinationId,
                inclusive = inclusive,
                saveState = saveState
            )
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()

    }
}