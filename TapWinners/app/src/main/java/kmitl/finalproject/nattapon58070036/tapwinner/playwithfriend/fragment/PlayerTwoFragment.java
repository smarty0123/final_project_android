package kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thunderrise.animations.PulseAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kmitl.finalproject.nattapon58070036.tapwinner.R;
import kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend.VersusFriendActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerTwoFragment extends Fragment {
    @BindView(R.id.pbTimer)
    ProgressBar pbTimer;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    @BindView(R.id.player2Fragment)
    ConstraintLayout player2Fragment;
    Unbinder unbinder;
    @BindView(R.id.tvScore)
    TextView tvScore;

    private int score = 0;

    private PlayerTwoFragmentListener listener;

    private VersusFriendActivity activity;

    public PlayerTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (VersusFriendActivity) getActivity();
        try {
            listener = (PlayerTwoFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement PlayerTwoFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        pbTimer.setVisibility(View.INVISIBLE);
        tvTimer.setVisibility(View.INVISIBLE);
        setAnimationView();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.player2Fragment)
    public void onViewClicked(final View view) {
        score++;
        tvScore.setText(String.valueOf(score));
        if (score == 1) { //player start tap
            listener.onPlayerTwoPlaying();
            pbTimer.setVisibility(View.VISIBLE);
            tvTimer.setVisibility(View.VISIBLE);

            CountDownTimer cdt = new CountDownTimer(6000, 50) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(String.valueOf(Long.toString((millisUntilFinished / 1000) + 1)));
                }

                @Override
                public void onFinish() {
                    view.setOnClickListener(null);
                    pbTimer.setVisibility(View.INVISIBLE);
                    tvTimer.setVisibility(View.INVISIBLE);
                    tvScore.setText("Your Score \n" + score);
                    activity.getPlayerTwoScore(score);
                    listener.onPlayerTwoFinished();
                }
            }.start();
        }

    }

    private void setAnimationView() {
        PulseAnimation.create().with(tvScore)
                .setDuration(500)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();
    }

    public interface PlayerTwoFragmentListener {
        void onPlayerTwoFinished();
        void onPlayerTwoPlaying();
    }
}
