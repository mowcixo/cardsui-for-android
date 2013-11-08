cardsui-for-android
===================

Forked from nadavfima/cardsui-for-android.

This fork pretends to adapt the library to tablet devices, trying to look like Google Now.

## Usage

### Layout XML

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >
 
    <com.fima.cardsui.views.CardUI
        android:id="@+id/cardsview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
 
</RelativeLayout>
```

### Activity class

```java
// init CardView
mCardView = (CardUI) findViewById(R.id.cardsview);
mCardView.setSwipeable(false);
 
// add AndroidViews Cards
mCardView.addCard(new MyCard("Get the CardsUI view"));
mCardView.addCardToLastStack(new MyCard("for Android at"));
MyCard androidViewsCard = new MyCard("www.androidviews.net");
androidViewsCard.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.androidviews.net/"));
                startActivity(intent);
 
            }
        });
mCardView.addCardToLastStack(androidViewsCard);
 
// add one card, and then add another one to the last stack.
mCardView.addCard(new MyCard("2 cards"));
mCardView.addCardToLastStack(new MyCard("2 cards"));
 
// add one card
mCardView.addCard(new MyCard("1 card"));
 
// create a stack
CardStack stack = new CardStack();
stack.setTitle("title test");
 
// add 3 cards to stack
stack.add(new MyCard("3 cards"));
stack.add(new MyCard("3 cards"));
stack.add(new MyCard("3 cards"));
 
// add stack to cardView
mCardView.addStack(stack);
 
// draw cards
mCardView.refresh();
```

## Known issues

* Sometimes when we touch on a card stack title, when it rearranges, the GridView cannot get the full height, and the inmediate bottom stack overlapes it.
* ...

## Responsive

The view arranges the cards BY DEFAULT by this way:

* When the device is a mobile (< LARGE) the view will have only a colum, in both landscape and portrait, and the column width will be 60% in landscape and 93% in portrait.
* When the device is a small tablet (LARGE) the view will have one column in portrait and two in landscape, and the view width (all the columns) will be 76% in landscape and 92% in portrait.
* When the device is a big tablet (> LARGE/XLARGE) the view will the same columns that the LARGE devices, but the view width will be 76% in both landscape and portrait.

This behavior has been extracted from Google Now behavior in the same devices described.

## Splashes

### Galaxy Nexus (< LARGE)

#### Landscape
![alt tag](https://raw.github.com/mowcixo/cardsui-for-android/master/snapshots/galaxy_nexus_land.png)
#### Portrait
![alt tag](https://raw.github.com/mowcixo/cardsui-for-android/master/snapshots/galaxy_nexus_port.png)

### Nexus 7 (LARGE)

#### Landscape
![alt tag](https://raw.github.com/mowcixo/cardsui-for-android/master/snapshots/nexus_7_land.png)
#### Portrait
![alt tag](https://raw.github.com/mowcixo/cardsui-for-android/master/snapshots/nexus_7_port.png)

### Galaxy Tab 10.1 (> LARGE/XLARGE)

#### Landscape
![alt tag](https://raw.github.com/mowcixo/cardsui-for-android/master/snapshots/galaxy_tab_10_land.png)
#### Portrait
![alt tag](https://raw.github.com/mowcixo/cardsui-for-android/master/snapshots/galaxy_tab_10_port.png)