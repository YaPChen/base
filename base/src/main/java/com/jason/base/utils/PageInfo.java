package com.jason.base.utils;

public class PageInfo implements IPageInterface
{
	  public static final int DEFAULT_PAGEINDEX = 1;
	  public static final int PER_PAGE_SIZE = 25;
	  public static final int MAX_PAGE_SIZE = 50;
	  public long mTotalCount;
	  public String mTag;
	  public int mPageIndex = 1;
	  public int mPageSize = 25;

	  public void reset()
	  {
	    this.mPageSize = 25;
	    this.mPageIndex = 1;
	    this.mTotalCount = 0L;
	  }

	  public void computePageSize(int newPageSize)
	  {
	    if (newPageSize > 50)
	      this.mPageSize = 50;
	    else if (newPageSize < 25)
	      this.mPageSize = 25;
	    else
	      this.mPageSize = newPageSize;
	  }
	}