package com.fima.cardsui.views;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.fima.cardsui.R;
import com.fima.cardsui.StackAdapter;
import com.fima.cardsui.Utils;
import com.fima.cardsui.objects.AbstractCard;
import com.fima.cardsui.objects.Card;
import com.fima.cardsui.objects.CardStack;

public class CardUI extends FrameLayout {

	/**
	 * Constants
	 */

	private static final int STATE_ONSCREEN = 0;
	private static final int STATE_OFFSCREEN = 1;
	private static final int STATE_RETURNING = 2;

	public interface OnRenderedListener {
		public void onRendered();
	}

	/********************************
	 * Fields
	 * 
	 ********************************/

	private ArrayList<AbstractCard> mStacks;
	private Context mContext;
	private ViewGroup mQuickReturnView;
	/**
	 * The table layout to be used for multiple columns
	 */
	private TableLayout mTableLayout;
	/**
	 * The number of columns, 1 by default
	 */
	private int mColumnNumber = 1;
	private View mPlaceholderView;
	private QuickReturnListView mListView;
	private int mMinRawY = 0;
	private int mState = STATE_ONSCREEN;
	private int mQuickReturnHeight;
	private int mCachedVerticalScrollRange;
	private boolean mSwipeable = false;
	private OnRenderedListener onRenderedListener;
	protected int renderedCardsStacks = 0;

	protected int mScrollY;
	private StackAdapter mAdapter;
	private View mHeader;
	
	private GridView mGridView;

	/**
	 * Constructor
	 */
	public CardUI(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//read the number of columns from the attributes
		mColumnNumber = attrs.getAttributeIntValue(null, "columnCount", 1);
		initData(context);
	}

	/**
	 * Constructor
	 */
	public CardUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		//read the number of columns from the attributes
		mColumnNumber = attrs.getAttributeIntValue(null, "columnCount", 1);
		initData(context);
	}

	/**
	 * Constructor
	 */
	public CardUI(Context context) {
		super(context);
		initData(context);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void initData(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		mStacks = new ArrayList<AbstractCard>();

		inflater.inflate(R.layout.cards_view, this);
		//mTableLayout = (TableLayout) findViewById(R.id.tableLayout);
		mGridView = (GridView)findViewById(R.id.gridview);

	    
	    ViewTreeObserver observer = getViewTreeObserver();
	    observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
	        public void onGlobalLayout() {

				setResponsiveLayoutParams();
		
				if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
	        }
	    });
	    
	}

	public void setSwipeable(boolean b) {
		mSwipeable = b;
	}

	public void setHeader(View header) {
		mPlaceholderView.setVisibility(View.VISIBLE);
	}
	
	public void changeColumns(int cols) {
		mColumnNumber = cols;
		if( mTableLayout != null )
			mTableLayout.removeAllViews();
		//mTableLayout = null;
		//initData(mContext);
	}

    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onConfigurationChanged(Configuration newConfig) {	    
	    ViewTreeObserver observer = getViewTreeObserver();
	    observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
	        public void onGlobalLayout() {

				setResponsiveLayoutParams();
				
		
				if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
	        }
	    });
	    
		super.onConfigurationChanged(newConfig);
	}

    private void setResponsiveLayoutParams() {
    	int currentOrientation = Utils.getScreenOrientation((Activity)mContext);
    	
    	FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		
    	int percent = 90;
    	int portraitColumns = 1;
    	int landscapeColumns = 2;
    	if( Utils.isXLarge(mContext) ) {
    		percent = 76;
    	}else if( Utils.isLarge(mContext) ) {
    		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
    			percent = 76;
	        }else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
	        	percent = 92;
	        }
    	}else {
    		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
    			percent = 60;
	        }else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
	        	percent = 93;
	        }
    		portraitColumns = 1;
    		landscapeColumns = 1;
    	}

	    if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridView.setNumColumns(landscapeColumns);
        }else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mGridView.setNumColumns(portraitColumns);
        }
    	
    	int width = (getWidth() * percent) / 100;
		int padding = ( getWidth() - width ) / 2;
    	
		lp.gravity = Gravity.CENTER_HORIZONTAL;

		mGridView.setLayoutParams(lp);

		int topAndBottom = Utils.convertDpToPixelInt(mContext, 6);
		
		mGridView.setPadding(padding, topAndBottom, padding, topAndBottom);
		
		mGridView.invalidate();
		mGridView.invalidateViews();
    }
    
	public void scrollToCard(int pos) {
		// int y = 0;
		try {
			// y = getY(pos);

			mListView.smoothScrollToPosition(pos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollToY(int y) {

		try {

			mListView.scrollTo(0, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QuickReturnListView getScrollView() {
		return mListView;
	}

	public int getLastCardStackPosition() {

		return mStacks.size() - 1;
	}

	public void addCard(Card card) {

		addCard(card, false);

	}

	public void addCard(Card card, boolean refresh) {

		CardStack stack = new CardStack();
		stack.add(card);
		mStacks.add(stack);
		if (refresh)
			refresh();

	}

	public void addCardToLastStack(Card card) {
		addCardToLastStack(card, false);

	}

	public void addCardToLastStack(Card card, boolean refresh) {
		if (mStacks.isEmpty()) {
			addCard(card, refresh);
			return;
		}
		int lastItemPos = mStacks.size() - 1;
		CardStack cardStack = (CardStack) mStacks.get(lastItemPos);
		cardStack.add(card);
		mStacks.set(lastItemPos, cardStack);
		if (refresh)
			refresh();

	}

	public void addStack(CardStack stack) {
		addStack(stack, false);

	}

	public void addStack(CardStack stack, boolean refresh) {
		mStacks.add(stack);
		if (refresh)
			refresh();

	}
	//suppress this error message to be able to use spaces in higher api levels
	@SuppressLint("NewApi")
	public void refresh() {

		if (mAdapter == null) {
			mAdapter = new StackAdapter(mContext, mStacks, mSwipeable);
			mGridView.setAdapter(mAdapter);
		} else {
			mAdapter.setSwipeable(mSwipeable); // in case swipeable changed;
			mAdapter.setItems(mStacks);
		}

	}

	public void clearCards() {
		mStacks = new ArrayList<AbstractCard>();
		renderedCardsStacks = 0;
		refresh();
	}

	public void setCurrentStackTitle(String title) {
		CardStack cardStack = (CardStack) mStacks
				.get(getLastCardStackPosition());
		cardStack.setTitle(title);

	}

	public OnRenderedListener getOnRenderedListener() {
		return onRenderedListener;
	}

	public void setOnRenderedListener(OnRenderedListener onRenderedListener) {
		this.onRenderedListener = onRenderedListener;
	}

}
