<h1 align="center">CringeHub <br /> (WIP)</h1>
<p align="center">
Itz yor hub of Cringe :)
</p>
<p align="center">
  <a href="https://github.com/RomaZykov/CringeHub/blob/master/README.md">
    <img src="https://img.shields.io/badge/lang-en-yellow" />
  </a>
  <a href="https://github.com/RomaZykov/CringeHub/blob/master/README.ru.md">
    <img src="https://img.shields.io/badge/%D1%8F%D0%B7%D1%8B%D0%BA-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B9-orange" />
  </a>
</p>
<p align="center">

## Description
  A gamified mobile application for boomers and buzzers to immerse themselves in the environment of modern culture (in different countries) of memes, information business, games, slang and various Internet practices with a learning mechanism.
  
  First of all, for me, as a developer, this application is a testing ground for various hypotheses and features, since the application's subject matter is not limited to the application itself and it is possible to add various crazy jokes, secondly, it is an attempt to reach monetization in the future and delight my favorite users with cool features and training materials.
<br /><br />  Inspired by: Lurkmore,  [HyperSkill](https://hyperskill.org/), [Lyra](https://www.gaugash.ru/lyra).

## Screenshots (so far only from Figma)
<p align="center">
<img src="https://github.com/RomaZykov/CringeHub/blob/master/demo/Demo%20Cringehub%201.png">
</p>
<p align="center">
<img src="https://github.com/RomaZykov/CringeHub/blob/master/demo/Demo%20Cringehub%202.png">
</p>

## Stack
  - Kotlin (KTS), Kotlin coroutines, Flow
  - MVVM, Clean Architecture
  - Single-Activity
  - Jetpack Compose
  - WorkManager
  - Hilt
  - Konsist
  - Firebase: Auth, Cloud Firestore

## Features (product side)
  - [x] Google signIn/signUp
  - [ ] Lessons on topics (where YouTubers steal content from, etc.)
  - [ ] Store (collections of memes for a certain year/other countries, a guide to buzzer slang, etc.)
  - [ ] Level Up's and the equivalent
  - [ ] ??? Secret Epic Feature

## Features (admin panel side)
  - [x] Only login via Email and password, no registration directly, manual registration via Firebase
  - [x] Drafts of the created content (offline-first approach based on push synchronization)
  - [ ] Practice as addition for topic

## Features (development)
  - Design from Figma (I did it)
  - Composite builds through convention plugins
  - Multi-module feature and layers based architecture
  - Custom theme
  - Inspired by [NowInAndroid](https://github.com/android/nowinandroid) (without the InDirectionalFlow approach, but with the classic clean architecture from Uncle Bob)

## Tests
<!-- 
1) Скриншот тесты
  From the above section you’ll notice that there were two commands we used – updateDebugScreenshotTest and validateDebugScreenshotTest. With these commands we need to make sure that our screenshots are kept up-to-date with the latest changes in our project, but we don’t want to be updating them all of the time – as we could accidentally update screenshots with UI regressions.

  For validateDebugScreenshotTest, we’ll want to run this whenever code is being committed to the project – so ideally on pull requests, failing the request if the check fails.

  When it comes to updateDebugScreenshotTest, we’ll only want to run this when there are intended changes made to our UI. Some examples of this could include:

  - making a change to a component in our design system
  - adding a new component to a pre-existing screen
  - adding a new screen that we want to have screenshot tests for
  With the examples above, we can see that we only want to run this update command when we are making intended changes to screens and/or components. It could also be the case that we have a pull request that makes intended and unintended changes – so it could be possible to accidentally update screenshots when it was not intended to.

  To avoid any accident changes, updateDebugScreenshotTest should not be run automatically by CI and any screenshot changes in pull requests should be flagged be automation so that changes can be checked by reviewers.

2) Приложите примеры кода и способы, как их запустить. Таким образом, вы сможете продемонстрировать, что вы уверены в том, что ваш проект будет работать без каких-либо проблем. Это позволит другим людям также поверить в успех этого проекта.-->

## Copyright
<a href="https://fonts.google.com/specimen/M+PLUS+Rounded+1c/license" title="fox icons">Шрифт M PLUS Rounded 1c</a>
