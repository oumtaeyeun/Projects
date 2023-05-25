import pygame
import random
import csv

# Set up the game window
pygame.init()
WIDTH = 800
HEIGHT = 600
screen = pygame.display.set_mode((WIDTH, HEIGHT))
MAX_SCORE = 100
pygame.display.set_caption("HCE")

# Set up the target
target_image = pygame.image.load("C:\\Users\\oumta\\Desktop\\2022 Spring\\2023 spring\\Data\\RP\\target.png").convert_alpha()
target_rect = target_image.get_rect()
target_rect.centerx = random.randint(0, WIDTH)
target_rect.centery = random.randint(0, HEIGHT)

# Set up the score
score = 0
accuracy = float(0.00)
font = pygame.font.SysFont("Arial", 30)
time_limit = 50  # in seconds
start_time = pygame.time.get_ticks()
time_left = time_limit * 1000  # in milliseconds

def on_game_end(score, accuracy, time_elapsed):
    # Scale the score to a range of 0 to 100
    scaled_score = (score / MAX_SCORE) * 100

    accuracy = float(accuracy)
    
    # Write the game result to a CSV file
    with open("C:\\Users\\oumta\\Desktop\\2022 Spring\\2023 spring\\Data\\RP\\score_data.csv", mode='a') as csv_file:
        fieldnames = ['accuracy', 'time_elapsed', 'score']
        writer = csv.DictWriter(csv_file, fieldnames=fieldnames)
        
        # If the file is empty, write the header row
        if csv_file.tell() == 0:
            writer.writeheader()
        
        # Write the game result to a new row
        writer.writerow({'accuracy': accuracy, 'time_elapsed': time_elapsed / 1000, 'score': scaled_score})

# Start the game loop
running = True
while running:
    # Handle events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        elif event.type == pygame.MOUSEBUTTONDOWN:
            # Check if the click hit the target
            if target_rect.collidepoint(event.pos):
                score += 1
                accuracy = float(accuracy + 1.00) / float(accuracy + 1.00)
                target_rect.centerx = random.randint(0, WIDTH)
                target_rect.centery = random.randint(0, HEIGHT)
            else:
                accuracy = float(accuracy) / float(accuracy + 1.00)
                
    # Update the timer
    time_elapsed = pygame.time.get_ticks() - start_time
    time_left = max(time_limit * 1000 - time_elapsed, 0)

    # End the game if the timer runs out
    if time_left == 0:
        running = False
        on_game_end(score, accuracy, time_elapsed)

    # Draw the screen
    screen.fill((255, 255, 255))
    screen.blit(target_image, target_rect)
    score_text = font.render("Score: {}".format(score), True, (0, 0, 0))
    screen.blit(score_text, (10, 10))
    timer_text = font.render("Time: {:.2f}".format(time_left / 1000), True, (0, 0, 0))
    screen.blit(timer_text, (WIDTH - 150, 10))
    pygame.display.flip()


# Clean up
pygame.quit()


