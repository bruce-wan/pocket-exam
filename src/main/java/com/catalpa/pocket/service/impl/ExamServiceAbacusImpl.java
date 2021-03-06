package com.catalpa.pocket.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.catalpa.pocket.entity.ExamPaper;
import com.catalpa.pocket.enums.ExamLevelEnum;
import com.catalpa.pocket.enums.QuestionTypeEnum;
import com.catalpa.pocket.error.ResourceNotFoundException;
import com.catalpa.pocket.mapper.ExamPaperMapper;
import com.catalpa.pocket.model.ExamData;
import com.catalpa.pocket.model.QuestionData;
import com.catalpa.pocket.service.ExamService;
import com.catalpa.pocket.util.RandomUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
@Log4j2
@Service("abacusExamService")
public class ExamServiceAbacusImpl implements ExamService {

    private final ExamPaperMapper examPaperMapper;

    @Autowired
    public ExamServiceAbacusImpl(ExamPaperMapper examPaperMapper) {
        this.examPaperMapper = examPaperMapper;
    }

    @Override
    public ExamData generateExamData(Integer catelog, Integer level) {
        JSONObject levelContent = JSONObject.parseObject(ExamLevelEnum.getByLevel(level).getContent());

        int digit = levelContent.getIntValue("digit");
        int size = levelContent.getIntValue("size");
        int amount = levelContent.getIntValue("amount");

        ExamData examData = new ExamData();
        examData.setCatalog(catelog);
        examData.setLevel(level);
        examData.setName(String.format("%d位数%d笔的加减法珠心算", digit, size));
        examData.setTotalScore(amount);
        examData.setPassScore(amount);

        List<QuestionData> questionDataList = new ArrayList<>();
        for (int index = 0; index < amount; index++) {
            boolean isComplete = false;
            int sum = 0;
            int i = 1;

            QuestionData questionData = new QuestionData();
            questionData.setCatalog(catelog);
            questionData.setLevel(level);
            questionData.setType(QuestionTypeEnum.BLANK_FILL.getType());

            StringBuilder title = new StringBuilder();
            do {
                int maxInt = 9 * digit;
                int minInt = -9 * digit;
                int nextInt = RandomUtil.randomInteger(minInt, maxInt);

                if(nextInt != 0) {
                    int tempSum = 0;
                    if (i == 1 && nextInt > 0) {
                        title.append(nextInt);
                        i++;
                        sum = nextInt;
                    } else if (i > 1 && i < size) {
                        tempSum = sum + nextInt;
                        if (tempSum >= 0 && tempSum <= maxInt) {
                            if(nextInt >= 0) {
                                title.append('+').append(nextInt);
                            } else {
                                title.append('-').append(Math.abs(nextInt));
                            }
                            i++;
                            sum = sum + nextInt;
                        }
                    } else if (i == size) {
                        tempSum = sum + nextInt;
                        if (tempSum >= 0 && tempSum <= maxInt) {
                            if(nextInt >= 0) {
                                title.append('+').append(nextInt);
                            } else {
                                title.append('-').append(Math.abs(nextInt));
                            }
                            sum = sum + nextInt;
                            isComplete = true;
                        }
                    }
                }
            } while (!isComplete);
            questionData.setTitle(title.toString());
            questionData.setAnswer(String.valueOf(sum));

            questionDataList.add(questionData);
        }
        examData.setQuestionDatas(questionDataList);
        return examData;
    }

    @Override
    public ExamData getExamDataById(Integer catelog, Long examId) {

        ExamPaper examPaper = examPaperMapper.selectById(examId);

        ExamData examData = null;
        if (examPaper != null) {
            examData = new ExamData();
            examData.setId(examPaper.getId());
            examData.setUserId(examPaper.getUserId());
            examData.setName(examPaper.getName());
            examData.setCatalog(examPaper.getCatalog());
            examData.setLevel(examPaper.getLevel());
            examData.setTotalScore(examPaper.getTotalScore());
            examData.setPassScore(examPaper.getPassScore());
            examData.setUserScore(examPaper.getUserScore());
            examData.setScoreGrade(examPaper.getScoreGrade());
            examData.setStartTime(examPaper.getStartTime());
            examData.setEndTime(examPaper.getEndTime());
            examData.setDuration(examPaper.getDuration());
            examData.setRemark(examPaper.getRemark());
            List<QuestionData> questionDatas = JSONArray.parseArray(examPaper.getContent(), QuestionData.class);
            examData.setQuestionDatas(questionDatas);
        } else {
            String message = "ExamData is not exists with examId is " + examId;
            log.error(message);
            throw new ResourceNotFoundException("40001", message);
        }

        return examData;
    }

}
