package com.cs520group8.roommatematcher.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.entity.UserSurvey;
import com.cs520group8.roommatematcher.repository.SurveyRepository;
import com.cs520group8.roommatematcher.repository.UserRepository;

@Service
public class ScoreServiceImpl implements ScoreService {
    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;

    public ScoreServiceImpl(UserRepository userRepository, SurveyRepository surveyRepository) {
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
    }

    public int scoreUsers(Long user1Id, Long user2Id) {
        int score = 0;
        Optional<User> user1Opt = userRepository.findById(user1Id);
        Optional<User> user2Opt = userRepository.findById(user2Id);

        if (user1Opt.isEmpty() || user2Opt.isEmpty()) {
            return 0;
        }
        User user1 = user1Opt.get();
        User user2 = user2Opt.get();
        Optional<UserSurvey> user1SurveyOpt = surveyRepository.findByUserId(user1.getId());
        Optional<UserSurvey> user2SurveyOpt = surveyRepository.findByUserId(user2.getId());

        if (user1SurveyOpt.isEmpty() || user2SurveyOpt.isEmpty()) {
            return 0;
        }

        UserSurvey user1Survey = user1SurveyOpt.get();
        UserSurvey user2Survey = user2SurveyOpt.get();

        String user1sleep = user1Survey.getSleepSchedule() == null
                ? null
                : user1Survey.getSleepSchedule().toString();
        String user2sleep = user2Survey.getSleepSchedule() == null
                ? null
                : user2Survey.getSleepSchedule().toString();

        String user1food = user1Survey.getFoodType() == null
                ? null
                : user1Survey.getFoodType().toString();
        String user2food = user2Survey.getFoodType() == null
                ? null
                : user2Survey.getFoodType().toString();

        String user1personality = user1Survey.getPersonalityType() == null
                ? null
                : user1Survey.getPersonalityType().toString();
        String user2personality = user2Survey.getPersonalityType() == null
                ? null
                : user2Survey.getPersonalityType().toString();

        String user1smoking = user1Survey.getSmokingPreference() == null
                ? null
                : user1Survey.getSmokingPreference().toString();
        String user2smoking = user2Survey.getSmokingPreference() == null
                ? null
                : user2Survey.getSmokingPreference().toString();

        String user1pets = user1Survey.getPetsPreference() == null
                ? null
                : user1Survey.getPetsPreference().toString();
        String user2pets = user2Survey.getPetsPreference() == null
                ? null
                : user2Survey.getPetsPreference().toString();

        if (!(user1sleep == null || user2sleep == null)) {
            if (user1sleep.equals(user2sleep)) {
                // add 1 if schedule is equal, but if both are flexible we do not add 1.
                if (!user1sleep.equalsIgnoreCase("FLEXIBLE")) {
                    score += 1;
                }
            } else {
                // If not equal, and no one is flexible, then score = score - 1
                if (!(user1sleep.equalsIgnoreCase("FLEXIBLE")
                        || user2sleep.equalsIgnoreCase("FLEXIBLE"))) {
                    score -= 1;
                }
            }
        }

        if (!(user1food == null || user2food == null)) {
            if (user1food.equals(user2food)) {
                // add 1 if food preference is equal, but if both are flexible we do not add 1.
                if (!user1food.equalsIgnoreCase("FLEXIBLE")) {
                    score += 1;
                }
            } else {
                // If not equal, and no one is flexible, then score = score - 1
                if (!(user1food.equalsIgnoreCase("FLEXIBLE")
                        || user2food.equalsIgnoreCase("FLEXIBLE"))) {
                    score -= 1;
                }
            }
        }

        if (!(user1personality == null || user2personality == null)) {
            if (user1personality.equals(user2personality)) {
                // add 1 if personality is equal, but if both are ambivert we do not add 1.
                if (!user1personality.equalsIgnoreCase("AMBIVERT")) {
                    score += 1;
                }
            } else {
                // If not equal, and no one is flexible, then score = score - 1
                if (!(user1personality.equalsIgnoreCase("AMBIVERT")
                        || user2personality.equalsIgnoreCase("AMBIVERT"))) {
                    score -= 1;
                }
            }
        }

        if (!(user1smoking == null || user2smoking == null)) {
            if (user1smoking.equals(user2smoking)) {
                score += 1;
            } else {
                score -= 1;
            }
        }

        if (!(user1pets == null || user2pets == null)) {
            if (user1pets.equals(user2pets)) {
                score += 1;
            } else {
                score -= 1;
            }
        }

        return score;
    }
}
