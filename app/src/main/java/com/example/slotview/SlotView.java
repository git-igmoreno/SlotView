package com.example.slotview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class SlotView extends androidx.appcompat.widget.AppCompatTextView {

    public final int DEFAULT_OFFSET = 5;
    public final int DEFAULT_DELAY = 5;
    private final String ENDL = "\0";
    private final String NBSP= " ";
    private int delay = DEFAULT_DELAY;
    private int offset = DEFAULT_OFFSET;
    private String fromText, toText;

    public SlotView(Context context) {
        super(context);
    }

    public SlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    public void slotTo(String to){
        this.toText = to;
        this.fromText = getNormalizedString(getText().toString().trim(), to);
        this.toText = getNormalizedString(to, fromText);
        SlotTask slotTask = new SlotTask();
        slotTask.execute();
    }

    public String replaceChar(int pos, char ch, String str){
        char[] charArray = str.toCharArray();
        charArray[pos] = ch;
        return new String(charArray);
    }

    public String getNormalizedString(String s1, String s2){
        int diff = lenghtDiff(s2, s1);
        return s1 += diff>=0 ? whiteSpaces(diff) : ENDL;
    }

    public String whiteSpaces(int count){
        return  new String(new char[count]).replace(ENDL, NBSP);
    }
    public int lenghtDiff(String s1, String s2){
        return s1.length()-s2.length();
    }

    public char getChar(char c, int offset){
        c = (char)(c-offset);
        return c;
    }


    private class SlotTask extends AsyncTask<Void, String, Void>
    {
        protected Void doInBackground(Void... params)
        {
            int pos =0;
            int relOffset = getOffset();

            while(pos< toText.length()) {
                try {

                    char next = toText.charAt(pos);
                    char curr = getChar(toText.charAt(pos), relOffset);
                    if(next==NBSP.charAt(0)) curr=next;
                    fromText = replaceChar(pos, curr, fromText);
                    publishProgress(fromText);
                    relOffset--;

                    if (curr == next){
                        pos++;
                        relOffset = getOffset();
                    }
                    Thread.sleep(getDelay());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            setText(fromText);
        }
    }
}