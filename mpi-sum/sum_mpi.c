#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc, char* argv[]) {
    int rank, size, N;
    int *data = NULL, *sub_data;
    int local_sum = 0, total_sum = 0;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == 0) {
        printf("Enter number of elements (N): ");
        scanf("%d", &N);
        if (N % size != 0) {
            printf("N must be divisible by number of processes.\n");
            MPI_Abort(MPI_COMM_WORLD, 1);
        }
        data = (int*)malloc(N * sizeof(int));
        printf("Enter %d elements:\n", N);
        for (int i = 0; i < N; i++)
            scanf("%d", &data[i]);
    }

    MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);
    int chunk_size = N / size;
    sub_data = (int*)malloc(chunk_size * sizeof(int));

    MPI_Scatter(data, chunk_size, MPI_INT, sub_data, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    for (int i = 0; i < chunk_size; i++)
        local_sum += sub_data[i];

    printf("Local sum at rank %d = %d\n", rank, local_sum);

    MPI_Reduce(&local_sum, &total_sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    if (rank == 0)
        printf("Final sum = %d\n", total_sum);

    if (data) free(data);
    free(sub_data);
    MPI_Finalize();
    return 0;
}

