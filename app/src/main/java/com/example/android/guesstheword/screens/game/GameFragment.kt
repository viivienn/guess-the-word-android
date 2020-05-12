
package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProvidersOf")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.setLifecycleOwner (this)

        viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()

        })

        viewModel.eventGameFinish.observe(this, Observer { hasFinished ->
            if(hasFinished){
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })
        return binding.root

    }



    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value?:0)
        findNavController(this).navigate(action)
    }


}
