package com.arctouch.io.outdoorsychallenge.searchrv

import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.robot.clickOnActionButton
import com.arctouch.io.outdoorsychallenge.robot.fillTextField
import com.arctouch.io.outdoorsychallenge.robot.matchGone
import com.arctouch.io.outdoorsychallenge.robot.matchVisible
import com.arctouch.io.outdoorsychallenge.robot.matchWithItemCount
import kotlinx.coroutines.runBlocking

object SearchRvRobot {
    fun clickOnSearchRvKeyboardActionButton() =
        clickOnActionButton(R.id.tiet_search_rv)

    fun typeOnSearchRvField(text: String) = fillTextField(R.id.tiet_search_rv, text)

    fun matchEmptyStateIsVisible() {
        matchVisible(R.id.tv_search_rv_empty_state_title)
        matchVisible(R.id.iv_search_rv_empty_state_image)
    }

    fun matchEmptyStateIsGone() {
        matchGone(R.id.tv_search_rv_empty_state_title)
        matchGone(R.id.iv_search_rv_empty_state_image)
    }

    fun matchAmountOfListedItems(amount: Int) =
        matchWithItemCount(R.id.rv_search_rv_results, amount)
}

fun searchRvState(block: suspend SearchRvRobot.() -> Unit) = runBlocking { SearchRvRobot.block() }
