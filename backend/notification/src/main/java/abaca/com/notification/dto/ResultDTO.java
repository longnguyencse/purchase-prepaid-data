package abaca.com.notification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ResultDTO<T> {
    @Builder.Default
    private boolean success = true;
    private String message;
    private T data;
    private int errorCode;
}
