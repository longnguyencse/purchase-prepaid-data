package abaca.com.prepaid.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
