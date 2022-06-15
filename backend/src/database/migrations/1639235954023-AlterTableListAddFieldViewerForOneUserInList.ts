import {
  MigrationInterface,
  QueryRunner,
  TableColumn,
  TableForeignKey,
} from 'typeorm';

export class AlterTableListAddFieldViewerForOneUserInList1639235954023
  implements MigrationInterface
{
  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.addColumn(
      'lists',
      new TableColumn({
        name: 'viewer_id',
        type: 'uuid',
        isNullable: true,
      }),
    );
    await queryRunner.createForeignKey(
      'lists',
      new TableForeignKey({
        name: 'relationUserViewerOfListOtherUser',
        referencedTableName: 'users',
        referencedColumnNames: ['id'],
        columnNames: ['viewer_id'],
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      }),
    );
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropForeignKey(
      'lists',
      'relationUserViewerOfListOtherUser',
    );
    await queryRunner.dropColumn('lists', 'viewer_id');
  }
}
