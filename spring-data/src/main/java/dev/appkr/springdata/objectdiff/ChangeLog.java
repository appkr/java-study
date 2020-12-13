package dev.appkr.springdata.objectdiff;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *   class ChangeLog {
 *     '수정시각
 *     - changedAt:DateTime
 *     '담당자
 *     - changedBy:UserDetails
 *     '수정항목
 *     - changedField:String
 *     '수정전
 *     - beforeValue:Object
 *     '수정후
 *     - afterValue:Object
 *     '비고 (메모)
 *     - note:String
 *   }
 */
@Getter
@NoArgsConstructor
@ToString
public class ChangeLog {

  private Instant changedAt = Instant.now();
  private String changedBy = "00000000-0000-0000-0000-000000000000";
  private String changedField;
  private Object beforeValue;
  private Object afterValue;
  private String memo;

  @Builder
  public ChangeLog(String changedField, Object beforeValue, Object afterValue, String memo) {
    this.changedField = changedField;
    this.beforeValue = beforeValue;
    this.afterValue = afterValue;
    this.memo = memo;
  }
}
