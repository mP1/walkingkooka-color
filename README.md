[![Build Status](https://github.com/mP1/walkingkooka-color/actions/workflows/build.yaml/badge.svg)](https://github.com/mP1/walkingkooka-color/actions/workflows/build.yaml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-color/badge.svg?branch=master)](https://coveralls.io/github/mP1/walkingkooka-color?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-color.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-color/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-color.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-color/alerts/)
![](https://tokei.rs/b1/github/mP1/walkingkooka-color)
[![J2CL compatible](https://img.shields.io/badge/J2CL-compatible-brightgreen.svg)](https://github.com/mP1/j2cl-central)



Represent color values in numerous color forms(rgb, rgba, hsl, hsla, hsv, hsva), color operations like mix, text and parsing support.

Small sample showing some supported operations.


```java
// internet or web name, default to BLACK if unknown....................
Color red = WebColorName.with("red")
	.orElse(Color.BLACK);

// parse rgba function....................................................
Color rgba = Color.parse("rgba(255,254,253,100%)");

// convert hsv to a rgb color.............................................
Color hsv = Color.parseHsv("hsv(270, 0.5, 0.25)")
     .toColor();

// mix the green component (3 to 1), preserving RBGA components...........
Color mixed = red.mix(hsv.green(), 0.75);

```

### [Converters](https://github.com/mP1/walkingkooka-convert/blob/master/src/main/java/walkingkooka/convert/Converter.java)

A collection of converters that are particularly useful within expressions and support passing colors as text.

- [ColorToColorConverter](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/convert/ColorToColorConverter.java)
- [ColorToNumberConverter](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/convert/ColorToNumberConverter.java)
- [NumberToColorConverter](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/convert/NumberToColorConverter.java)
- [TextToColorConverter](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/convert/TextToColorConverter.java)

### [Functions](https://github.com/mP1/walkingkooka-tree/blob/master/src/main/java/walkingkooka/tree/expression/function/ExpressionFunction.java)

Functions that will be useful with `Color`

- [color](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionColor.java)
- [getAlpha](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionGetAlpha.java)
- [getBlue](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionGetBlue.java)
- [getGreen](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionGetGreen.java)
- [getRed](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionGetRed.java)
- [invertColor](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionInvertColor.java)
- [mixColor](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionMixColor.java)
- [setAlpha](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionSetAlpha.java)
- [setBlue](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionSetBlue.java)
- [setGreen](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionSetGreen.java)
- [setRed](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionSetRed.java)
- [toGray](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionRgbColorToGray.java)
- [toRgbColor](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionToRgbColor.java)
- [toRgbHexString](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionToRgbHexString.java)
- [toWebColorName](https://github.com/mP1/walkingkooka-color/blob/master/src/main/java/walkingkooka/color/expression/function/ColorExpressionFunctionToWebColorName)
