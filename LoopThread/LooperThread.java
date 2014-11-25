/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2014, ticktick <lujun.hust@gmail.com>
 *  http://ticktick.blog.51cto.com/
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    LooperThread.java 
 *  @brief   带Looper的线程封装
 *  
 *  @version 1.0     
 *  @author  ticktick
 *  @date    2014/10/15   
 * 
 */
package com.ticktick.juncode.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LooperThread {
	
    private volatile boolean mIsLooperQuit = false;
		
    private Thread mThread;
    private Callbak mCallbak;
    
    private Lock mLock = new ReentrantLock();
    private Condition mCondition = mLock.newCondition();
    private Queue<Message> mMessageQueue = new LinkedList<Message>();
    
    public static class Message {
    	int what;
    }
    
    public static interface Callbak {
    	public boolean handleMessage(Message msg);
    }
    
    public LooperThread( Callbak callback ) {
    	mCallbak = callback;
    }

    public void start() {		
	if( mThread != null ) {
		return;
	}		
	mIsLooperQuit = false;
	mThread = new Thread(mLooperRunnable);
	mThread.start();		
    }

    public void stop() {		
	if( mThread == null ) {
		return;
	}		
	mIsLooperQuit = true;	
	
	mLock.lock();    	
        mCondition.signal();
        mLock.unlock();

        mThread.interrupt();
        try {
	    mThread.join(1000);
        } 
        catch (InterruptedException e) {		
	    e.printStackTrace();
        }
        mMessageQueue.clear();
        mThread = null;		
    }

    public void sendMessage( Message message ) {
	if( mThread == null ) {
		return;
	}		
	mLock.lock();
        mMessageQueue.add(message);
        mCondition.signal();
        mLock.unlock();
    }

    protected Runnable mLooperRunnable = new Runnable() {		
	
	@Override
	public void run() {
		
    	    while( !mIsLooperQuit ) {
		
		mLock.lock();
		
		Message message = null;
		try {			
		    while( !mIsLooperQuit && mMessageQueue.isEmpty() ) {				
			mCondition.await();
		    } 
		    message = mMessageQueue.poll();					
		}
		catch (InterruptedException e) {
		    e.printStackTrace();			
		}
		finally {
		    mLock.unlock();
		}									
		
		if( mCallbak != null && message != null ) {
		    mCallbak.handleMessage(message);
		}									
	    }
        }
    };		
}
