package areas;

import java.util.ArrayList;

/**
 * Created by VanitaZ on 2015-02-26.
 */
public class MemoCollector {

    private ArrayList<AreaMemo> memoList;

    public MemoCollector () {
        this.memoList = new ArrayList<AreaMemo>();
    }

    public int getMemoNum () {
        return memoList.size();
    }

    public void addMemo (AreaMemo memo) {
        memoList.add(memo);
    }

    public AreaMemo getMemo (int i) {
        if (i <= memoList.size() && i > 0)
            return memoList.get(i-1);
        else
            return null;
    }

    public void clearMemos () {
        this.memoList.clear();
    }

}
