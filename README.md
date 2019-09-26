[![Build Status](https://travis-ci.com/mP1/walkingkooka-color.svg?branch=master)](https://travis-ci.com/mP1/walkingkooka-color.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-color/badge.svg?branch=master)](https://coveralls.io/github/mP1/walkingkooka-color?branch=master)

Represent color values in numerous color forms(rgb, rgba, hsl, hsla, hsv, hsva), color operations like mix, text and parsing support.

Small sample showing some supported operations.


```java
// internet or web name, default to BLACK if unknown....................
Color red = WebColorName.with("red")
	.orElse(Color.BLACK);

// parsea rgba function..................................................
Color rgba = Color.parse("rgba(255,254,253,100%)");

// convert hsv to a rgb color.............................................
Color hsv = Color.parseHsv("hsv(270, 0.5, 0.25)")
     .toColor();

// mix the green component (3 to 1), preserving RBA components............
Color mixed = red(hsv.green(), 0.75);

```