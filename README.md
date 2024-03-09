# Instagram Clone Project

## Overview

This project is a hands-on learning experience focused on Android development using XML and Kotlin. The goal is to
replicate some key features of the Instagram app, helping you understand the fundamentals of Android UI design and
functionality.

**Note:** This project is solely for educational purposes, and the code is inspired by Instagram. It is not intended for
commercial use.

## Features

- [X] Login and Registration: Implement user authentication for a seamless user experience.
- [X] Home Feed: Design a feed where users can view posts from people they follow.
- [X] Profile Page: Create a user profile page with details and posts.
- [X] Upload Post: Allow users to share images with captions on their feed.
- [ ] Comments and Likes: Enable users to interact with posts through comments and likes.

## Getting Started

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/your-username/instagram-clone.git
    ```

2. Open the project in Android Studio.
3. Run the app on an emulator or a physical device.

## Screenshots

### Login Screen:

![](/Users/felipe/Development/android-express/Instagram/documentation/login.png "Login Screen Image")

### Home Feed:

![](./Instagram/documentation/home.png "Home Feed Image")

### Profile Page:

![](./Instagram/documentation/profile.png "Profile Page Image")

### Upload Post:

![](./Instagram/documentation/upload_post.png "Upload Post Image")

[//]: # (### Comments and Likes:)

[//]: # (TODO)

## Dependencies

```kotlin
dependencies {
    implementation 'de.hdodenhof:circleimageview:3.1.0'

    implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.0'

    implementation 'androidx.fragment:fragment-ktx:1.3.6'

    implementation 'androidx.camera:camera-camera2:1.0.1'
    implementation 'androidx.camera:camera-lifecycle:1.0.1'
    implementation 'androidx.camera:camera-view:1.0.0-alpha27'

    implementation platform ('com.google.firebase:firebase-bom:28.4.2')
    implementation 'com.google.firebase:firebase-auth-ktx'
    implementation 'com.google.firebase:firebase-firestore-ktx'
    implementation 'com.google.firebase:firebase-storage-ktx'

    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
}
```

## Resources

[Android Developer Documentation]([URL](https://developer.android.com/develop))
[Kotlin Documentation](https://kotlinlang.org/docs/home.html)

## License

<!-- This project is licensed under the MIT License - see the LICENSE file for details. -->

## Acknowledgments

- Instagram for providing inspiration for this educational project.
- The Android and Kotlin communities for valuable resources and support.
- Tiago Aguiar for creating the training "android express"

Happy coding! 🚀
