package web.banking.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TransferReqDto {
    private String fromAccountNumber;
    private String toAccountNumber;
    private Integer amount;
}
