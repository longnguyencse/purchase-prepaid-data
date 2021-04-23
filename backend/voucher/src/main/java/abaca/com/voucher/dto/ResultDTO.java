package abaca.com.voucher.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ResultDTO {
    @Builder.Default
    private boolean  success = true;
    private String message;
    private Object data;
    private int errorCode;
}
