package com.michaelflisar.materialpreferences.preferencescreen.input

import com.michaelflisar.dialogs.DialogInput

// why not the dialogs state? just to not force users to include the dialog library... and its just one enum...
enum class InputState {
    None,
    Focus,
    SelectAll;

    internal val dialogState: DialogInput.State
        get() {
            return when (this) {
                None -> DialogInput.State.None
                Focus -> DialogInput.State.Focus
                SelectAll -> DialogInput.State.SelectAll
            }
        }
}