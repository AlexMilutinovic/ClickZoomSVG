# ClickZoomSVG
Temporary project for adding click and zoom functionalities on SVG in Android
## IMPORTANT
Project is used just for as a code sample. It is not final application, neither contains full code. I am not able to share full neither partial working code, but only this. So, this will not be maintained at all (but contains crutial parts for you to make it work :) ). Contact me for additional details or any help (I'll be glad to help if I can).

Code can be optimized a lot, for sure, so fee free to do whatever you want :)

### Hints
**Important Dependencies:**
- [Lukle/ClickableAreasImages](https://github.com/Lukle/ClickableAreasImages) - Makes a SVG clickable (read their instructions for help)
- [BigBadaboom/androidsvg](https://github.com/BigBadaboom/androidsvg) - Used their code, changed it a little (to support additional stuff)
- [japgolly/svg-android](https://github.com/japgolly/svg-android) - Used this one also as a help and reference

**'Usage'**
```// Parse SVG:
SVG svg = SVGParser.getSVGFromResource(getResources(), R.raw.world_map);

// Get image view (this is our SVG map that is going to be clickable and zoomable):
ImageView touchImageView = (ImageView) view.findViewById(R.id.touchImageView);

// Create drawable (will convert svg to drawable so we can set it to image view as a bitmap):
PictureDrawable image = svg.createPictureDrawable();
Bitmap b = drawableToBitmap(image); // refer to WorldMapFragment for source code of the converting method 
touchImageView.setImageBitmap(b);

// Create image with clickable areas from previous image view and set listener to id (listener of type OnClickableAreaClickedListener created in onAttach callback):
ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(touchImageView), listener);
  
// Define your clickable areas
// parameter values (pixels): (x coordinate, y coordinate, width, h	eight) and assign an object to it
List<ClickableArea> clickableAreas = new ArrayList<>();
Map<String, FintemCity> cities = svg.getCities();
for (String key : cities.keySet()){
  clickableAreas.add(cities.get(key).toClickableArea(getMetrics()));
}
  
// Set your clickable areas to the image
clickableAreasImage.setClickableAreas(clickableAreas);
```
---

Please refer primarly to WorldMapFragment for core of the solution. Other things are probably unnecesarry.
Many details can be found on dependencies' repos, so look for it.

There is an World Map svg demo for which this code works.

Additionally, I'll be gratefull if someone use this and make a nice HowTo, I really don't have time for it.

Cheers!
