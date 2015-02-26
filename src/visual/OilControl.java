package visual;

import areas.Area;
import areas.AreaMemo;
import areas.MemoCollector;
import cons.EDir;

import java.awt.*;

/**
 * Created by VanitaZ on 2015-02-24.
 */
public class OilControl {

    private Area area;
    private OilView view;
    private OilControl control;

    private MemoCollector memos;
    private int backNum = 0;

    public OilControl () {

        control = this;

        this.memos = new MemoCollector();
        this.area = new Area(false);
        memos.addMemo(area.createMemo());
        area.generateRandomSource();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                view = new OilView(area.getMapGrid(),control);
                view.setVisible(true);

            }
        });
    }

    public void nextIterations (int count) {
        returnMemo();
        for (int i = 0; i < count; i++) {
            area.calculateNewOilLevelForAll();
            area.updateOilLevelForAll();
        }
        view.areaUpdated();
        memos.addMemo(area.createMemo());
    }

    public void generateArea() {
        memos.clearMemos();
        area.generateRandomMapGrid();
        view.changeArea(area.getMapGrid());
        memos.addMemo(area.createMemo());
    }

    public void setSpillAtSelected() {
        area.generateSource(view.getSelectedRow(),view.getSelectedColumn());
        memos.addMemo(area.createMemo());
    }

    public void setWind (EDir dir) {
        area.setWindDirection(dir);
        memos.addMemo(area.createMemo());
    }

    public void setWind (float pow) {
        area.setWindPower(pow);
        memos.addMemo(area.createMemo());
    }

    public void setInfo (int y, int x) {
        view.setInfo(area.getCellInfo(y, x));
    }

    public void test () {
        memos.clearMemos();
        area.generateTestMapGrid();
        view.changeArea(area.getMapGrid());
        memos.addMemo(area.createMemo());
    }

    public void undo () {
        ++backNum;
        AreaMemo memo = memos.getMemo(memos.getMemoNum() - backNum);
        if (memo != null)
            area.setMemo(memo);
        view.areaUpdated();
    }

    public void redo () {
        --backNum;
        AreaMemo memo = memos.getMemo(memos.getMemoNum() - backNum);
        if (memo != null)
            area.setMemo(memo);
        view.areaUpdated();
    }

    public void returnMemo () {
        AreaMemo memo = memos.getMemo(memos.getMemoNum());
        if (memo != null)
            area.setMemo(memo);
        view.areaUpdated();
    }

}
