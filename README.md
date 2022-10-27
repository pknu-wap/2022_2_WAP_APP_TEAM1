<h1 align="center">2022 2학기 APP 1팀 WitT</h1>

<p align="center">
    <img src="https://img.shields.io/badge/Kotlin-1.7.20-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white"/>
    <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
    <img src="https://img.shields.io/badge/node.js-339933?style=for-the-badge&logo=Node.js&logoColor=white"/>
    <img src="https://img.shields.io/badge/oracleDB-F80000?style=for-the-badge&logo=oracle&logoColor=white">
    <img src="https://img.shields.io/badge/all_contributors-4-orange.svg?style=for-the-badge"/>
</p>

<p align="center">
	<h3 align="center">
		 공유 플랜 여행서비스<br>WitT(With Trip)
	</h3>	

</p>

<h2>Android Tech Stack</h2>

- Minumum SDK 23
- Kotlin
    - Coroutines & Flow 
- Dagger-Hilt
- Room
- Android Jetpack
    - LiveData & ViewModel
    - Navigation Component
    - SharedPreference
    - DataBinding
- Retrofit2 & OkHttp3 & Moshi
- Glide
- Material Components
- Canhub ImageCropper
- hdodenhof CircleImageView

<h2>Android Architecture</h2>

WitT는 Android 공식문서에 서술된 [Android App Architecture](https://developer.android.com/topic/architecture#recommended-app-arch)를 기반으로 작성하였습니다.

<p align="center">
  <img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview.png" width="50%"/>
</p>


## Contributors ✨

컴퓨터를 누르면 우리팀의 활동을 볼 수 있어요!

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="http://github.com/jeongjaino"><img src="https://avatars.githubusercontent.com/u/77484719?v=4" width="100px;" alt=""/><br /><sub><b>JinHo Jeong</sub></a><br /><a href="https://github.com/pknu-wap/2022_2_WAP_APP_TEAM1/commits/develop_android?author=jeongjaino" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/SeongHoonC"><img src="https://avatars.githubusercontent.com/u/108349655?v=4" width="100px;" alt=""/><br /><sub><b>SeongHoon Choi</b></sub></a><br /><a href="https://github.com/pknu-wap/2022_2_WAP_APP_TEAM1/commits/develop_android?author=SeongHoonC" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/winocreative"><img src="https://avatars.githubusercontent.com/u/26576118?v=4" width="100px;" at=""/><br /><sub><b>JaeWan Seo</b></sub></a><br /><a href="https://github.com/pknu-wap/2022_2_WAP_APP_TEAM1/commits/develop_server?author=winocreative" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/Yenniiii"><img src="https://avatars.githubusercontent.com/u/102972001?v=4" width="100px;" at=""/><br /><sub><b>YeEun Lee</b></sub></a><br /><a href="https://github.com/pknu-wap/2022_2_WAP_APP_TEAM1/commits/develop_server?author=Yenniiii" title="Code">💻</a></td>
  </tr>
    <tr>
    <td align="center">안드로이드</td>
    <td align="center">안드로이드</td>
    <td align="center">백엔드</td>
    <td align="center">백엔드</td>
  </tr>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->


## Packages 📁

```
Android WitT
 ┣ 📂application
 ┣ 📂data
 ┃ ┣ 📂api
 ┃ ┣ 📂source
 ┃ ┣ 📂mapper
 ┃ ┣ 📂model
 ┃ ┃ ┣ 📂request
 ┃ ┃ ┗ 📂response
 ┃ ┗ 📂repository
 ┣ 📂di
 ┣ 📂domain
 ┃ ┣ 📂use_case
 ┃ ┣ 📂model
 ┃ ┗ 📂repository
 ┣ 📂presentaion
 ┃ ┣ 📂adapter
 ┃ ┣ 📂ui
 ┃ ┗ 📂base
 ┗ 📂util
```
