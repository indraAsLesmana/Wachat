package com.example.indraaguslesmana.wachat.adapter;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;

import com.example.indraaguslesmana.wachat.Fragment.FragmentCall;
import com.example.indraaguslesmana.wachat.Fragment.FragmentChat;
import com.example.indraaguslesmana.wachat.Fragment.FragmentContact;

/**
 * Created by indraaguslesmana on 2/8/17.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter{
    private final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "CALL", "CHATS", "CONTACTS"};
    private Context mContext;

    private final int FRAGMENT_CALL = 0;
    private final int FRAGMENT_CHAT = 1;
    private final int FRAGMENT_CONTACT = 2;


    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FRAGMENT_CALL:
                return new FragmentCall();
            case FRAGMENT_CHAT:
                return new FragmentChat();
            case FRAGMENT_CONTACT:
                return new FragmentContact();
            default:
                return new FragmentChat();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableString sb = new SpannableString("   "+ tabTitles[position]);

        return sb;
    }
}
