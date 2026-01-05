# MPAndroidChart-Compose

[![Release](https://img.shields.io/github/release/Amir-yazdanmanesh/MPAndroidChart-Compose.svg?style=flat)](https://jitpack.io/#Amir-yazdanmanesh/MPAndroidChart-Compose)
[![API](https://img.shields.io/badge/API-21%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![GitHub stars](https://img.shields.io/github/stars/Amir-yazdanmanesh/MPAndroidChart-Compose?style=social)](https://github.com/Amir-yazdanmanesh/MPAndroidChart-Compose/stargazers)

A powerful & easy to use chart library for Android, updated for modern Android development with Jetpack Compose example app.

Based on the original [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) by Philipp Jahoda.

## Support This Project

If you find this library useful, please consider giving it a star! Your support helps keep this project alive and motivates continued development and maintenance.

[![Star this repo](https://img.shields.io/github/stars/Amir-yazdanmanesh/MPAndroidChart-Compose?style=for-the-badge&logo=github)](https://github.com/Amir-yazdanmanesh/MPAndroidChart-Compose)

## Table of Contents
1. [Support This Project](#support-this-project)
2. [Quick Start](#quick-start)
   1. [Gradle Setup](#gradle-setup)
   2. [Maven Setup](#maven-setup)
3. [Documentation](#documentation)
4. [Examples](#examples)
5. [Questions & Issues](#questions--issues)
6. [More Examples](#more-examples)
7. [License](#license)
8. [Credits](#credits)

## Quick Start

### Gradle Setup

Add JitPack repository to your `settings.gradle` or root `build.gradle`:

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your module's `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.Amir-yazdanmanesh:MPAndroidChart-Compose:v3.2.1'
}
```

### Maven Setup

```xml
<!-- <repositories> section of pom.xml -->
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<!-- <dependencies> section of pom.xml -->
<dependency>
    <groupId>com.github.Amir-yazdanmanesh</groupId>
    <artifactId>MPAndroidChart-Compose</artifactId>
    <version>v3.2.1</version>
</dependency>
```

## Documentation

See the [**documentation**](https://weeklycoding.com/mpandroidchart/) for examples and general use of MPAndroidChart.

## Examples

Check out the example app in the [MPChartExample](https://github.com/Amir-yazdanmanesh/MPAndroidChart-Compose/tree/master/MPChartExample) directory. The example app has been rewritten using **Jetpack Compose**.

## Questions & Issues

This repository's issue tracker is for bugs and feature requests.

Please read the [**documentation**](https://weeklycoding.com/mpandroidchart/) first, then ask questions on [stackoverflow.com](https://stackoverflow.com/questions/tagged/mpandroidchart) using the `mpandroidchart` tag.

## More Examples

**LineChart (with legend, simple design)**

![alt tag](https://raw.github.com/PhilJay/MPChart/master/screenshots/simpledesign_linechart4.png)

**LineChart (cubic lines)**

![alt tag](https://raw.github.com/PhilJay/MPChart/master/screenshots/cubiclinechart.png)

**LineChart (gradient fill)**

![alt tag](https://raw.github.com/PhilJay/MPAndroidChart/master/screenshots/line_chart_gradient.png)

**BarChart (with legend, simple design)**

![alt tag](https://raw.github.com/PhilJay/MPChart/master/screenshots/simpledesign_barchart3.png)

**BarChart (grouped DataSets)**

![alt tag](https://raw.github.com/PhilJay/MPChart/master/screenshots/groupedbarchart.png)

**Horizontal-BarChart**

![alt tag](https://raw.github.com/PhilJay/MPChart/master/screenshots/horizontal_barchart.png)

**Combined-Chart (bar- and linechart in this case)**

![alt tag](https://raw.github.com/PhilJay/MPChart/master/screenshots/combined_chart.png)

**PieChart (with selection)**

![alt tag](https://raw.github.com/PhilJay/MPAndroidChart/master/screenshots/simpledesign_piechart1.png)

**ScatterChart** (with squares, triangles, circles, ... and more)

![alt tag](https://raw.github.com/PhilJay/MPAndroidChart/master/screenshots/scatterchart.png)

**CandleStickChart** (for financial data)

![alt tag](https://raw.github.com/PhilJay/MPAndroidChart/master/screenshots/candlestickchart.png)

**BubbleChart** (area covered by bubbles indicates the yValue)

![alt tag](https://raw.github.com/PhilJay/MPAndroidChart/master/screenshots/bubblechart.png)

**RadarChart** (spider web chart)

![alt tag](https://raw.github.com/PhilJay/MPAndroidChart/master/screenshots/radarchart.png)

## License

Copyright 2020 Philipp Jahoda

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

> http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Credits

Based on the original [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) by [Philipp Jahoda](https://github.com/PhilJay).

Special thanks to:
- [danielgindi](https://github.com/danielgindi) - Daniel Gindi
- [mikegr](https://github.com/mikegr) - Michael Greifeneder
