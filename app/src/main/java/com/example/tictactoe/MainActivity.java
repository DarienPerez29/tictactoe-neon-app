package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private List<MaterialButton> board_cells;
    private List<int[]> winner_combinations;

    private static final int[] CELLS_IDS = {
            R.id.cell_1,
            R.id.cell_2,
            R.id.cell_3,
            R.id.cell_4,
            R.id.cell_5,
            R.id.cell_6,
            R.id.cell_7,
            R.id.cell_8,
            R.id.cell_9,
    };

    private TextView winner_text, winner_display, turn_text, turn_display;
    private String current_turn = "x";
    private boolean winner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board_cells = new ArrayList<>(CELLS_IDS.length);
        for (int id : CELLS_IDS) {
            MaterialButton cell = (MaterialButton) findViewById(id);
            board_cells.add(cell);
        }

        winner_combinations = new ArrayList<>();
        winner_combinations.add(new int[]{0, 1, 2});
        winner_combinations.add(new int[]{3, 4, 5});
        winner_combinations.add(new int[]{6, 7, 8});
        winner_combinations.add(new int[]{0, 3, 6});
        winner_combinations.add(new int[]{1, 4, 7});
        winner_combinations.add(new int[]{2, 5, 8});
        winner_combinations.add(new int[]{0, 4, 8});
        winner_combinations.add(new int[]{2, 4, 6});

        winner_text = findViewById(R.id.win_text);
        winner_display = findViewById(R.id.win_display);
        turn_text = findViewById(R.id.turn_text);
        turn_display = findViewById(R.id.turn_display);
    }

    // Set symbols on board cells
    public void setSymbol(MaterialButton cell) {
        String player_text;
        int player_color;
        int player_glow;

        // Validate if cell is empty or if there's no winner to write player text, if not, skip
        if (cell.getText().toString().equals("") && !winner) {
            if (current_turn.equals("x")) { // Set x player attributes
                player_text = "x";
                player_color = ResourcesCompat.getColor(getResources(), R.color.x_player, null);
                player_glow = ResourcesCompat.getColor(getResources(), R.color.x_glow, null);
                current_turn = "o";
            } else { // Set o player attributes
                player_text = "o";
                player_color = ResourcesCompat.getColor(getResources(), R.color.o_player, null);
                player_glow = ResourcesCompat.getColor(getResources(), R.color.o_glow, null);
                current_turn = "x";
            }

            cell.setText(player_text);
            cell.setTextColor(player_color);
            cell.setShadowLayer(20, 0, 0, player_glow);

            int [] win_data = validateWinner(player_text);
            if (win_data[0] == 1) {
                displayWinner(player_text, player_color, player_glow, win_data[1], win_data[2], win_data[3]);
                winner = true;
            } else {
                validateDraw();
            }

            turn_display.setText(current_turn);
        }
    }

    // Validate if there's a winner
    public int[] validateWinner(String played) {
        int symbols_in_a_row = 0;

        for (int[] combination : winner_combinations) {
            for (int id : combination)
                if (board_cells.get(id).getText().toString().equals(played)) symbols_in_a_row++;

            if (symbols_in_a_row == 3)
                return IntStream.concat(Arrays.stream(new int[]{1}), Arrays.stream(combination)).toArray();

            symbols_in_a_row = 0;
        }

        return new int[]{0};
    }

    // Validate if there's a draw
    public void validateDraw(){
        int filled_cells = 0;

        for (MaterialButton cell : board_cells) {
            if (!cell.getText().toString().equals("")) filled_cells++;
        }

        if (filled_cells == 9) {
            winner = true;

            winner_text.setText("EMPATE");
            winner_display.setText("");
            winner_display.setPadding(0,0,0,0);

            winner_text.setVisibility(View.VISIBLE);
            winner_display.setVisibility(View.VISIBLE);
            turn_text.setVisibility(View.INVISIBLE);
            turn_display.setVisibility(View.INVISIBLE);
        }
    }

    // Set elements to winner status
    public void displayWinner(String match_winner, int player_color, int player_glow, int cw1, int cw2, int cw3) {
        for (MaterialButton cell : board_cells) {
            cell.setTextColor(ResourcesCompat.getColor(getResources(), R.color.disabled, null));
            cell.setShadowLayer(0, 0, 0, 1);
        }

        board_cells.get(cw1).setTextColor(player_color);
        board_cells.get(cw1).setShadowLayer(20, 0, 0, player_glow);
        board_cells.get(cw2).setTextColor(player_color);
        board_cells.get(cw2).setShadowLayer(20, 0, 0, player_glow);
        board_cells.get(cw3).setTextColor(player_color);
        board_cells.get(cw3).setShadowLayer(20, 0, 0, player_glow);

        winner_display.setText(match_winner);
        winner_display.setTextColor(player_color);
        winner_display.setShadowLayer(20, 0, 0, player_glow);

        winner_text.setVisibility(View.VISIBLE);
        winner_display.setVisibility(View.VISIBLE);
        turn_text.setVisibility(View.INVISIBLE);
        turn_display.setVisibility(View.INVISIBLE);
    }

    public void cell1OnClick(View view) {
        setSymbol(board_cells.get(0));
    }

    public void cell2OnClick(View view) {
        setSymbol(board_cells.get(1));
    }

    public void cell3OnClick(View view) {
        setSymbol(board_cells.get(2));
    }

    public void cell4OnClick(View view) {
        setSymbol(board_cells.get(3));
    }

    public void cell5OnClick(View view) {
        setSymbol(board_cells.get(4));
    }

    public void cell6OnClick(View view) {
        setSymbol(board_cells.get(5));
    }

    public void cell7OnClick(View view) {
        setSymbol(board_cells.get(6));
    }

    public void cell8OnClick(View view) {
        setSymbol(board_cells.get(7));
    }

    public void cell9OnClick(View view) {
        setSymbol(board_cells.get(8));
    }

    // Restart all the game
    public void restartBoard(View view) {
        board_cells.forEach(cell -> cell.setText(""));

        current_turn = "x";
        turn_display.setText(current_turn);

        winner_text.setText("HA GANADO");
        winner_display.setPadding(15,15,15,15);

        winner_text.setVisibility(View.INVISIBLE);
        winner_display.setVisibility(View.INVISIBLE);
        turn_text.setVisibility(View.VISIBLE);
        turn_display.setVisibility(View.VISIBLE);

        winner = false;
    }
}