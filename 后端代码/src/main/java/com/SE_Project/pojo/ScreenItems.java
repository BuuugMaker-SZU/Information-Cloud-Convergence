package com.SE_Project.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenItems {
    private List<String> hostSelectedItems; //主办单位
    private List<String> selectedItems; //活动类型
}

