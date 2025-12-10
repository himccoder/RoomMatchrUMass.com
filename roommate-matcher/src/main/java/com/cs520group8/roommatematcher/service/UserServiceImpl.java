package com.cs520group8.roommatematcher.service;

import org.springframework.stereotype.Service;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.entity.UserSurvey;
import com.cs520group8.roommatematcher.repository.SurveyRepository;
import com.cs520group8.roommatematcher.repository.UserRepository;
import com.cs520group8.roommatematcher.dto.UserDTO;
import com.cs520group8.roommatematcher.dto.UserFilterRequest;
import com.cs520group8.roommatematcher.dto.UserWithScoreDTO;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final ScoreService scoreService;
    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;

    public UserServiceImpl(UserRepository userRepository, SurveyRepository surveyRepository,
            ScoreService scoreService) {
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
        this.scoreService = scoreService;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> retList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDto = new UserDTO();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            retList.add(userDto);
        }
        return retList;
    }

    @Override
    public List<UserDTO> getFilteredUsers(UserFilterRequest filters) {
        List<User> users = userRepository.findAll();
        List<UserDTO> retList = new ArrayList<>();

        for (User user : users) {
            UserDTO userDto = new UserDTO();
            Optional<UserSurvey> surveyOpt = surveyRepository.findByUserId(user.getId());

            if (surveyOpt.isEmpty()) {
                continue;
            }

            UserSurvey survey = surveyOpt.get();

            if (filters.getSleepSchedule() != null &&
                    survey.getSleepSchedule() != filters.getSleepSchedule()) {
                continue;
            }

            if (filters.getFoodType() != null &&
                    survey.getFoodType() != filters.getFoodType()) {
                continue;
            }

            if (filters.getPersonalityType() != null &&
                    survey.getPersonalityType() != filters.getPersonalityType()) {
                continue;
            }

            if (filters.getPetsPreference() != null &&
                    survey.getPetsPreference() != filters.getPetsPreference()) {
                continue;
            }

            if (filters.getSmokingPreference() != null &&
                    survey.getSmokingPreference() != filters.getSmokingPreference()) {
                continue;
            }

            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            retList.add(userDto);
        }

        return retList;
    }

    public List<UserWithScoreDTO> getRecommendedUsers(Long userId) {
        class TempClassForSort {
            UserWithScoreDTO dto;
            int score;

            public TempClassForSort(UserWithScoreDTO dto, int score) {
                this.dto = dto;
                this.score = score;
            }
        }
        List<User> users = userRepository.findAll();
        List<TempClassForSort> tempList = new ArrayList<>();
        List<UserWithScoreDTO> retList = new ArrayList<>();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                continue;
            }
            int score = scoreService.scoreUsers(userId, user.getId());
            double maxScore = 5.0;
            UserWithScoreDTO userWithScoreDTO = new UserWithScoreDTO();
            userWithScoreDTO.setId(user.getId());
            userWithScoreDTO.setName(user.getName());
            userWithScoreDTO.setEmail(user.getEmail());
            userWithScoreDTO.setPercentMatch((score / maxScore) * 100);
            tempList.add(new TempClassForSort(userWithScoreDTO, score));
        }

        tempList.sort((a, b) -> Integer.compare(b.score, a.score));

        int counter = 0;
        for (TempClassForSort x : tempList) {
            if (counter > 2) {
                break;
            }
            retList.add(x.dto);
            counter++;
        }
        return retList;
    }

}
