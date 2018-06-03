package spittr.exception;

/**
 * 错误处理器
 * @author: monkey
 * @date: 2018/6/3 17:48
 */
public class SpittleNotFoundException extends RuntimeException {

    private Long spittleId;

    public SpittleNotFoundException(Long spittleId) {
        this.spittleId = spittleId;
    }

    public Long getSpittleId() {
        return spittleId;
    }
}
