# KingsLogin Android
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Release](https://jitpack.io/v/kingschat/KingsLogin-android.svg)](https://jitpack.io/#kingschat/KingsLogin-android)

This library allows your android app to login using Kingschat.

Written in Kotlin 1.3.0

Supported Android API version 19 and higher

# Installation Guide

Create your app in [Kingschat Dashboard](https://developer.kingsch.at/)
 
If you don't know how to generate your `Signature (SHA1)` follow [Developers Guide](https://developers.google.com/android/guides/client-auth)
 
## Gradle implementation
1. Add the JitPack repository to your root build.gradle:
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

2. Add the dependency to your sub build.gradle:
```gradle
dependencies {
    compile 'com.github.kingschat:KingsLogin-android:{lastest-version}'
}
``` 

## App implementation
1. Get your application `Client ID` from [Kingschat Dashboard](https://developer.kingsch.at/) and add it to manifest file
```xml
<meta-data
    android:name="com.kingschat.sdk.ApplicationId"
    android:value="Your client ID" />
``` 

2. Init KingsLogin library
```kotlin
KingsLogin.init(applicationContext)
```

3. Use `KingsLoginButton` or create your custom button in layout
```xml
<com.newmedia.kingslogin.widget.KingsLoginButton
  android:id="@+id/button"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content" />
```
        
4. In Activity register callbacks 

```kotlin
KingsLoginManager.getInstance().registerCallback(callbackManager, kingsloginCallback)
```

and handle it in `onActivityResult`

```kotlin
 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (callbackManager.onActivityResult(requestCode, resultCode, data))
        else
            super.onActivityResult(requestCode, resultCode, data)
    }
```

5. Instead of KingsLogin you can request permissions directly
```kotlin
KingsLogin.requestPermissions(this, listOf("user"))
```

## Sample
For more information about implementation check our sample app

