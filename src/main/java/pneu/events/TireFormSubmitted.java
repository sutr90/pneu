package pneu.events;

import pneu.vo.TireVO;

public class TireFormSubmitted {
    public final TireVO tireInfo;

    public TireFormSubmitted(TireVO tireVO) {
        this.tireInfo = tireVO;
    }
}
