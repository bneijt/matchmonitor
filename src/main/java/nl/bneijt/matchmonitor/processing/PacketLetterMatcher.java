package nl.bneijt.matchmonitor.processing;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import nl.bneijt.matchmonitor.StateDatabase;

import java.util.ArrayList;

public class PacketLetterMatcher implements PacketContentsHandler {
    public static ArrayList<String> letters = Lists.newArrayList(Splitter.fixedLength(1).split("abcdefghijklmnopqrstuvwxyz"));

    @Inject
    StateDatabase stateDatabase;

    @Override
    public void apply(String content) {
        for (String letter : letters) {
            if (content.contains(letter)) {
                stateDatabase.matched(System.currentTimeMillis(), letter);
                return;
            }
        }
    }
}
