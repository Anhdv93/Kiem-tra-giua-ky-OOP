package th.system.domain.constant;

import java.util.Arrays;
import java.util.List;

public enum RegisterType {
    SCHEDULED(2),
    FREE(1),
    NOT_SUPPORT(0);
    
    private int inputOpt;

    private RegisterType(int inputOpt) {
        this.inputOpt = inputOpt;
    }
    
    public int getInputOpt() {
        return inputOpt;
    }
    
    public static List<RegisterType> getOptList() {
        return Arrays.asList(values());
    }

    public static RegisterType getOptByValue(int inputOpt) {
        return getOptList().stream().filter(e -> e.inputOpt == inputOpt).findFirst().orElse(NOT_SUPPORT);
    }
}
