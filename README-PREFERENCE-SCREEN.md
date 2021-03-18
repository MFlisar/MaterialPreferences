### PreferenceScreen

This module allows you to create `RecyclerView` based preference screens with a DSL.

### Example

Here's an example and it's output.

```kotlin
val screen = screen {
    state = savedInstanceState
    category {
        title = "Test App Style".asText()
    }
    input(UserSettingsModel.name) {
        title = "Name".asText()
    }
    switch(UserSettingsModel.alive) {
        title = "Alive".asText()
    }
    subScreen {
        title = "More".asText()
        color(UserSettingsModel.hairColor) {
            title = "Hair Color".asText()
        }
        input(UserSettingsModel.age) {
            title = "Age".asText()
        }
    }
}
```

Check out the demo activities code for more details.

*Side note:*

This library uses my (AndroidText)[https://github.com/MFlisar/AndroidText] class for title and summaries. This is a minimal sealedclass (a few lines of code) and allows to use `Strings` and `Ints` via a common class and so avoids the necessity to add `resText` and `stringText` parameters to all classes that do support string or resources as texts as most other libraries do.
Usage of this class is very simply, I've created extension functions for `Strings` and `Ints` as you can see in the example.

### Default Settings

Some values can be defined globally and will be used by all preferences - those default values are stored in the `PreferenceScreenConfig` object. You can change dafault values like following:

```kotlin
PreferenceScreenConfig.apply {
    bottomSheet = true  // default: false
    maxLinesTitle = 1   // default: 1
    maxLinesSummary = 3 // default: 3
}
```

### Dependencies

The preferences with dialogs do depend on [Material Dialogs](https://github.com/afollestad/material-dialogs)

### Credits

Special thanks goes to [ModernAndroidPreferences](https://github.com/Maxr1998/ModernAndroidPreferences) because if copied a few things from there, namely following:
* the root layout xml
* the `RecyclerView` animations
* the badge idea and the badge drawable
* the basic idea for the DSL