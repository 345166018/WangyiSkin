# WangyiSkin

A dynamic skinning framework for Android applications that allows users to change the app's visual theme (colors, drawables, etc.) at runtime using an external APK file as the skin source.

## Description

`WangyiSkin` (implemented as `HxSkin`) provides a mechanism to decouple the UI styling from the main application binary. By utilizing a "skin APK," the app can load resources dynamically, enabling features like personalized themes or remote UI updates without requiring a full application update from the app store.

The project consists of two main modules:
- `app`: The main host application that consumes the skins.
- `app_skin`: A sample skin project used to generate the `.apk` file containing the theme resources.

## Key Features

- **Dynamic Resource Loading**: Load colors and drawables from an external APK.
- **Skin Restoration**: Easily revert to the default application theme.
- **Asset Extraction**: Built-in utility to extract skin APKs from the assets folder to the internal storage for loading.
- **Support for Android Components**: Demonstrates skinning for `TextView`, `Button`, and custom selectors.

## Installation & Setup

### Prerequisites
- Android Studio
- JDK 1.8
- Android SDK (API 24+ recommended)

### Integration
1. Clone the repository.
2. Open the `HxSkin` project in Android Studio.
3. Ensure the `app_skin` module is built and the resulting APK is placed in the `assets` folder of the `app` module.
   - The code expects a file named `app_skin-debug.apk` by default.

## Usage

### Initialization
Initialize the `SkinManager` in your `Application` class to prepare the skinning environment:

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
```

### Loading a Skin
To apply a skin from a file path:

```java
// Apply a skin from the specified APK path
SkinManager.getInstance().loadSkin(apkPath);

// Restore the default skin
SkinManager.getInstance().loadSkin(null);
```

### Extracting Skin from Assets
If the skin is bundled within the app's assets, use the provided `Utils` class to move it to the file system before loading:

```java
Utils.extractAssets(context, "app_skin-debug.apk");
```

## Project Structure

- `app/`: The host app containing the logic to switch and apply skins.
- `app_skin/`: The resource-only project used to create theme APKs.
- `com.hongx.skin.util.Utils`: Helper methods for file operations and asset extraction.
- `com.hongx.skincore.SkinManager`: The core engine responsible for resource overriding.
