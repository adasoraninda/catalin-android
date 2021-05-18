package creational.builder

class AlertDialog private constructor(
    val title: String? = null,
    val message: String? = null,
    val positiveButton: Boolean? = null,
    val negativeButton: Boolean? = null,
) {

    class Builder {
        private var title: String? = null
        private var message: String? = null
        private var positiveButton: Boolean? = null
        private var negativeButton: Boolean? = null

        fun setTitle(title: String) = apply {
            this.title = title
        }

        fun setMessage(message: String) = apply {
            this.message = message
        }

        fun setPositiveButton(positiveButton: Boolean) = apply {
            this.positiveButton = positiveButton
        }

        fun setNegativeButton(negativeButton: Boolean) = apply {
            this.negativeButton = negativeButton
        }

        fun build(): AlertDialog {
            return AlertDialog(
                title = title,
                message = message,
                positiveButton = positiveButton,
                negativeButton = negativeButton,
            )
        }

    }
}