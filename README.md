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

.......

## Splashes

.....