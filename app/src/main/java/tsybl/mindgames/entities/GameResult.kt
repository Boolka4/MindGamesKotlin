package tsybl.mindgames.entities

data class GameResult(val score: Int,
                      val start: Int,
                      val coins: Int,
                      val isShowBestScore: Boolean)