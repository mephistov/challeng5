package com.nicolascastilla.data

import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.domain.repositories.ChallengeRepository
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val dataBase: ChallengeDao
): ChallengeRepository {
}